from flask import Flask, request, jsonify
import pymysql
from datetime import datetime, timedelta

app = Flask(__name__)


def get_db_connection():
    return pymysql.connect(
        host='localhost',
        user='root',
        password='',
        db='moodtracker',
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )

def create_tables():
    conn = get_db_connection()
    with conn.cursor() as cur:
        cur.execute("""
            CREATE TABLE IF NOT EXISTS moods (
                id INT AUTO_INCREMENT PRIMARY KEY,
                mood VARCHAR(50) NOT NULL,
                note TEXT,
                timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """)
        conn.commit()
    conn.close()

@app.route('/api/mood', methods=['POST'])
def add_mood():
    data = request.get_json()
    mood = data.get('mood')
    note = data.get('note')
    timestamp = data.get('timestamp')

    if not mood or not timestamp:
        return jsonify({'error': 'Mood and timestamp are required'}), 400

    try:
        dt_object = datetime.fromtimestamp(timestamp / 1000)
    except Exception as e:
        return jsonify({'error': f'Invalid timestamp: {e}'}), 400

    conn = get_db_connection()
    with conn.cursor() as cur:
        cur.execute("""
            INSERT INTO moods (mood, note, timestamp)
            VALUES (%s, %s, %s)
        """, (mood, note, dt_object))
        conn.commit()
    conn.close()

    return jsonify({'message': 'Mood entry saved successfully'}), 201

@app.route('/api/history', methods=['GET'])
def get_history():
    conn = get_db_connection()
    with conn.cursor() as cur:
        cur.execute("SELECT mood, note, timestamp FROM moods ORDER BY timestamp DESC")
        rows = cur.fetchall()
    conn.close()

    return jsonify(rows), 200

@app.route('/api/summary', methods=['GET'])
def mood_summary():
    conn = get_db_connection()
    with conn.cursor() as cur:
        cur.execute("""
            SELECT mood, COUNT(*) as count
            FROM moods
            WHERE timestamp >= NOW() - INTERVAL 7 DAY
            GROUP BY mood
        """)
        rows = cur.fetchall()
    conn.close()

    summary = {row['mood']: row['count'] for row in rows}
    return jsonify({'moods': summary}), 200

if __name__ == '__main__':
    create_tables()
    app.run(host='0.0.0.0', port=5001, debug=True)
