from flask import Flask, request,jsonify
import sql
import MySQLdb
import defs
from datetime import timedelta
import md5
from flask_login import (LoginManager, login_required, login_user, 
                         current_user, logout_user, UserMixin)
from itsdangerous import URLSafeTimedSerializer
app = Flask(__name__)

conn = MySQLdb.connect(host=defs.HOST,
                       user=defs.USER,
                       passwd=defs.PASSWORD,
                       db=defs.DBASE)
conn.autocommit(True)

app.secret_key = "ASW12G2Z1123TASFSDFJN35-DS#123P+@@1"

login_serializer = URLSafeTimedSerializer(app.secret_key)

login_manager = LoginManager()


class User(UserMixin):
    def __init__(self, userid,userpk, password):
        self.id = userid
        self.userpk = userpk
        self.password = password
    
    def get_id(self):
        data = [self.id, self.password]
        return login_serializer.dumps(data)

    @staticmethod
    def get(token):
        for user in USERS:
            data = login_serializer.loads(token)
            if user.id == data[0]:
                return user
        return None
    @staticmethod
    def get_username(userid):
        for user in USERS:
            if user.id == userid:
                return user
        return None

def hash_pass(password):
    salted_password = password + app.secret_key
    return md5.new(salted_password).hexdigest()

@login_manager.user_loader
def load_user(token):
    return User.get(token)

@app.route("/test")
@login_required
def test():
    return "OK"
@app.route("/torneos")
@login_required
def torneos():
    return jsonify(sql.torneos(conn))

@app.route("/mapa")
@login_required
def mapa():
    return jsonify(sql.mapa(conn))

@app.route("/zonas")
@login_required
def zonas():
    return jsonify(sql.zonas(conn))

@app.route("/tipos")
@login_required
def tipos():
    return jsonify(sql.tipos(conn))

@app.route("/fechas")
@login_required
def fechas():
    return jsonify(sql.fechas(conn))

@app.route("/busqueda", methods=['GET', 'POST'])
@login_required
def busqueda():
    if(request.data):
        jdata=request.get_json()
        return jsonify(sql.busqueda(jdata,conn))
    return "none"
@app.route("/buscador")
@login_required
def buscador():
    return jsonify(sql.buscador(conn))

@app.route("/disponibilidad")
@login_required
def disponibilidad():
    return jsonify(sql.disponibilidad(request.args.get('predio'),conn))

@app.route("/reservar")
@login_required
def reservar():
    return sql.reservar(str(current_user.userpk),str(request.args.get('cancha')),str(request.args.get('fecha')),str(request.args.get('hora')),conn)

@app.route("/logout")
@login_required
def logout():
    logout_user()
    return "1"

@app.route("/login")
def login():
    user = User.get_username(request.args.get('user'))
    if user and hash_pass(request.args.get('pass')) == user.password:
        login_user(user, remember=True)
        return "1"
    return "0"

@app.route("/reservas")
@login_required
def reservas():
    return jsonify(sql.reservas(str(current_user.userpk),conn))
    
if __name__ == '__main__':
    print(" * La IP publica del servidor es 192.168.1.1:5000")
    app.debug = True
    login_manager.setup_app(app)
    app.config["REMEMBER_COOKIE_DURATION"] = timedelta(hours=2)
    USERS=[]
    for value in sql.all_users(conn):
    	USERS.append(User(value[1],value[0],value[2]))
    app.run(host='0.0.0.0',port=3000)
