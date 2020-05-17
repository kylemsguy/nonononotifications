from flask import Flask, send_file, request, jsonify
import facebook.ghostdetector as fb

app = Flask(__name__)

# Not secure, so using the hardcoded login files
# @app.route('/login', methods=['POST'])
# def login():
#     user = request.args['user']
#     password = request.args['password']
#     fb.setup(user, password)
#     return 'ok'

@app.route('/devicetoken', methods=['POST'])
def devicetoken():
    token = request.args['token']
    fb.set_device_token(token)
    return '200'

@app.route('/ghosted', methods=['GET'])
def checkifghosted():
    return jsonify(fb.get_result())



if __name__ == '__main__':
    app.run(host='127.0.0.1', port=int('5000'), debug=True)
