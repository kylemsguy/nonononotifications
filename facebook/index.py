from flask import Flask, send_file
import facebook.onreaddetector

app = Flask(__name__)

@app.route('/login')


@app.route('/ghosted')
def checkifghosted():
    pass


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=int('5000'), debug=True)