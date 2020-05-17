from flask import Flask, send_file, request, jsonify
import facebook.onreaddetector as fb

app = Flask(__name__)

@app.route('/login', methods=['POST'])
def login():
    user = request.args['user']
    password = request.args['password']
    fb.setup(user, password)
    return 'ok'

@app.route('/ghosted', methods=['GET'])
def checkifghosted():
    return jsonify(fb.get_result())


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=int('5000'), debug=True)