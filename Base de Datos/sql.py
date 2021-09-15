import MySQLdb
from datetime import date, timedelta,datetime
from redlock import RedLock, ReentrantRedLock, RedLockError
from redlock.lock import CLOCK_DRIFT_FACTOR
import mock
import time
import unittest
hcampo=[
      "DISPONIBILIDAD.HS00",
      "DISPONIBILIDAD.HS01",
      "DISPONIBILIDAD.HS02",
      "DISPONIBILIDAD.HS03",
      "DISPONIBILIDAD.HS04",
      "DISPONIBILIDAD.HS05",
      "DISPONIBILIDAD.HS06",
      "DISPONIBILIDAD.HS07",
      "DISPONIBILIDAD.HS08",
      "DISPONIBILIDAD.HS09",
      "DISPONIBILIDAD.HS10",
      "DISPONIBILIDAD.HS11",
      "DISPONIBILIDAD.HS12",
      "DISPONIBILIDAD.HS13",
      "DISPONIBILIDAD.HS14",
      "DISPONIBILIDAD.HS15",
      "DISPONIBILIDAD.HS16",
      "DISPONIBILIDAD.HS17",
      "DISPONIBILIDAD.HS18",
      "DISPONIBILIDAD.HS19",
      "DISPONIBILIDAD.HS20",
      "DISPONIBILIDAD.HS21",
      "DISPONIBILIDAD.HS22",
      "DISPONIBILIDAD.HS23"
      ]    
def torneos(conn):
    cur = conn.cursor()
    cur.execute('''SELECT TORNEO.TORNEO_PK,TORNEO.EQUIPO,TORNEO.FECHA_INICIO,
      TORNEO.PRECIO,TORNEO.PRECIOxPARTIDO,PREDIO.PREDIO,PREDIO.TELEFONO,PREDIO.DIRECCION,
      PREDIO.ZONA FROM PREDIO INNER JOIN TORNEO ON PREDIO.PREDIO_PK = TORNEO.PREDIO_FK''')
    res = [dict((cur.description[i][0], value) for i, value in enumerate(row)) for row in cur.fetchall()]
    cur.close()
    return res


def reservas(USER_PK,conn):
    cur = conn.cursor()

    cur.execute('''SELECT RESERVA.RESERVA_PK,RESERVA.FECHA,RESERVA.HORA,
      RESERVA.ESTADO,RESERVA.PRECIO,RESERVA.SENA,RESERVA.TIMESTAMP,
      CANCHA.CANCHA,CANCHA.TIPO,CANCHA.SUPERFICIE,CANCHA.TECHADO,
      PREDIO.PREDIO,PREDIO.TELEFONO,PREDIO.DIRECCION,PREDIO.ZONA 
      FROM RESERVA INNER JOIN CANCHA ON CANCHA.CANCHA_PK = RESERVA.CANCHA_FK 
      INNER JOIN PREDIO ON PREDIO.PREDIO_PK = CANCHA.PREDIO_FK  WHERE RESERVA.USUARIO_FK =%s''',(USER_PK))
    res = [dict((cur.description[i][0], value) for i, value in enumerate(row)) for row in cur.fetchall()]
    cur.close()
    return res

def mapa(conn):
    cur = conn.cursor()
    cur.execute("SELECT PREDIO.PREDIO,PREDIO.PREDIO_PK,PREDIO.GPS FROM PREDIO")
    res = [dict((cur.description[i][0], value) for i, value in enumerate(row)) for row in cur.fetchall()]
    cur.close()
    return res


def zonas(conn):
    cur = conn.cursor()
    cur.execute("SELECT ZONA FROM PREDIO GROUP BY ZONA")
    res = [dict((cur.description[i][0], value) for i, value in enumerate(row)) for row in cur.fetchall()]
    cur.close()
    return res

def tipos(conn):
    cur = conn.cursor()
    cur.execute("SELECT TIPO FROM CANCHA GROUP BY TIPO")
    res = [dict((cur.description[i][0], value) for i, value in enumerate(row)) for row in cur.fetchall()]
    cur.close()
    return res

def buscador(conn):
    aux=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    res=[]
    horas=[
          "00:00 A 01:00",
          "01:00 A 02:00",
          "02:00 A 03:00",
          "03:00 A 04:00",
          "04:00 A 05:00",
          "05:00 A 06:00",
          "06:00 A 07:00",
          "07:00 A 08:00",
          "08:00 A 09:00",
          "09:00 A 10:00",
          "10:00 A 11:00",
          "11:00 A 12:00",
          "12:00 A 13:00",
          "13:00 A 14:00",
          "14:00 A 15:00",
          "15:00 A 16:00",
          "16:00 A 17:00",
          "17:00 A 18:00",
          "18:00 A 19:00",
          "19:00 A 20:00",
          "20:00 A 21:00",
          "21:00 A 22:00",
          "22:00 A 23:00",
          "23:00 A 00:00"
          ]
    cur = conn.cursor()

    for value in horas:
        res.append(dict(hora=value))
    cur.execute("SELECT TIPO FROM CANCHA GROUP BY TIPO ORDER BY TIPO ASC")
    for value in cur.fetchall():
      res.append(dict(tipo=value[0]))
    cur.execute("SELECT ZONA FROM PREDIO GROUP BY ZONA ORDER BY ZONA ASC")
    for value in cur.fetchall():
      res.append(dict(zona=value[0]))
    cur.close()
    return res



def fechas(conn):
    cur = conn.cursor()

    cur.execute("SELECT FECHA FROM FECHA ORDER BY FECHA")
    res = cur.fetchall()
    cur.close()
    return res

def login(USER,PASS,conn):
    cur = conn.cursor()

    cur.execute("SELECT USUARIO_PK FROM USUARIO WHERE ( (USUARIO=%s) AND (CONTRASENA=%s) ) LIMIT 1",(USER,PASS))
    res = cur.fetchone()[0] if cur.rowcount else 0
    cur.close()
    return str(res)
def all_users(conn):
    cur = conn.cursor()
    cur.execute("SELECT USUARIO_PK,USUARIO,CONTRASENA FROM USUARIO")
    return cur.fetchall()

def busqueda(jdata,conn):

    jposta = {}
    tipo=[0,0,0]
    cur = conn.cursor()
    query = '''
    SELECT PREDIO.*,
    COUNT(VALORACION.VALORACION) AS CANTIDAD,
    CAST(AVG(VALORACION.VALORACION) AS SIGNED) AS VAL
    FROM PREDIO 
    INNER JOIN CANCHA ON CANCHA.PREDIO_FK = PREDIO.PREDIO_PK 
    INNER JOIN RESERVA ON RESERVA.CANCHA_FK = CANCHA.CANCHA_PK 
    INNER JOIN VALORACION ON VALORACION.RESERVA_FK = RESERVA.RESERVA_PK
    INNER JOIN DISPONIBILIDAD ON DISPONIBILIDAD.PREDIO_FK = PREDIO.PREDIO_PK 
    '''
    flag=0
    for field in jdata:
      for k in field:
        if k not in jposta:
          jposta[k] = []
        jposta[k].append(field[k])
    
    
    if len(jposta)>1 or (len(jposta)==1 and (("TIPO" in jposta) or ("ZONA" in jposta))) :
      query += "WHERE "
      if "TIPO" in jposta:
        flag=1
        query+="("
        for data in jposta["TIPO"]:
          query+="CANCHA.TIPO = '"+data+"' OR "
        query=query[:len(query)-4]
        query+=")"
      if flag>0:
        query+=" AND "
      if "ZONA" in jposta:
        flag=1
        query+="("
        for data in jposta["ZONA"]:
          query+="PREDIO.ZONA = '"+data+"' OR "
        query=query[:len(query)-4]
        query+=")"
      elif flag>0:
        query=query[:len(query)-5]
      if ("HORA" in jposta) and ("FECHA" in jposta):
        if flag>0:
          query+=" AND "
        query+="(PREDIO.PREDIO_PK NOT IN (SELECT PREDIO_FK FROM EXCEPCION WHERE ("
        for data in jposta["FECHA"]:
          day=datetime.strptime(data,"%d/%m/%Y")
          query+="FECHA = '" +day.strftime("%Y-%m-%d")+"' AND "
          cur.execute("SELECT FERIADO_PK FROM FERIADO WHERE FECHA = "+day.strftime("%Y-%m-%d"))
          if cur.rowcount:
            tipo[2]=1
          elif day.weekday()<5:
            tipo[0]=1
          else:
            tipo[1]=1
        query=query[:len(query)-4]
        query+="))) AND (("
        for data in jposta["HORA"]:
          query+=hcampo[int(data)]+" OR "
        query=query[:len(query)-4]
        query+=") IN ("
        if tipo[2]==1:
          query+="4,5,6,7,"
        if tipo[1]==1:
          query+="2,3,6,7,"
        if tipo[0]==1:
          query+="1,3,5,7,"
        query=query[:len(query)-1]
        query+="))" 
    query+= "GROUP BY PREDIO.PREDIO_PK ORDER BY PREDIO.PREDIO ASC"
    cur.execute(query)
    res = [dict((cur.description[i][0], value) for i, value in enumerate(row)) for row in cur.fetchall()]
    cur.close()
    return res

def reservar(USER,CANCHA,FECHA,HORA,conn):
    cur = conn.cursor()
    lock1 = RedLock((CANCHA+" - "+FECHA+" - "+HORA),[{"host":"localhost"}], ttl=1000)
    if USER=="0":
      return "-1"
    else:
      lock1_locked = lock1.acquire()
      if(lock1_locked==0):
        return "0"

      cur.execute('''
                  SELECT RESERVA_PK FROM RESERVA WHERE 
                  (FECHA = %s) AND (HORA = %s) AND (CANCHA_FK = %s) AND (ESTADO != 'CANCELADO') AND (ESTADO != 'EXPIRADO')
                  LIMIT 1
                  ''',(FECHA,HORA,CANCHA))
      if cur.rowcount:
        lock1.release()
        cur.close()
        return "0"
      else:
        cur.execute('''
                SELECT PREDIO.PRECIO,PREDIO.SENA 
                FROM PREDIO
                INNER JOIN CANCHA ON CANCHA.PREDIO_FK = PREDIO.PREDIO_PK 
                WHERE
                (CANCHA.CANCHA_PK=%s)
                LIMIT 1
                ''',CANCHA)
        res=cur.fetchone()
        cur.execute('''
                    INSERT INTO `RESERVA`(`FECHA`,`HORA`,`ESTADO`,`PRECIO`,`SENA`,`USUARIO_FK`,`CANCHA_FK`) 
                    VALUES (%s,%s,"RESERVADO",%s,%s,%s,%s)
                    ''',(FECHA,HORA,res[0],res[1],USER,CANCHA))
        lock1.release()
        cur.close()
        return "1"

def disponibilidad(PRE_PK,conn):
    res=[]
    cur = conn.cursor()
    flag=[1,3,5,7],[2,3,6,7],[4,5,6,7]
    for dias in range(7):
      day = date.today()+timedelta(days=dias)
      cur.execute("SELECT FERIADO_PK FROM FERIADO WHERE FECHA = '%s' "% day.strftime("%Y-%m-%d"))
      tipo = 2 if cur.rowcount else 0 if day.weekday()<5 else 1
      for INDEX in range(24):
        cur.execute('''
                    SELECT %s AS FECHA,%s AS HORA,CANCHA.CANCHA AS CANCHA,CANCHA.CANCHA_PK AS CANCHA_PK
                    FROM PREDIO 
                    INNER JOIN CANCHA ON CANCHA.PREDIO_FK = PREDIO.PREDIO_PK 
                    INNER JOIN DISPONIBILIDAD ON DISPONIBILIDAD.PREDIO_FK = PREDIO.PREDIO_PK 
                    WHERE 
                    (PREDIO.PREDIO_PK=%s) AND 
                    (PREDIO.PREDIO_PK NOT IN (SELECT PREDIO_FK FROM EXCEPCION WHERE (FECHA = %s) ) ) AND 
                    (CANCHA.CANCHA_PK NOT IN (SELECT CANCHA_FK FROM RESERVA WHERE (FECHA = %s) AND (HORA = %s) AND (ESTADO !='CANCELADO') AND (ESTADO !='EXPIRADO'))) AND 
                    ((('''+hcampo[INDEX]+''' IN (%s,%s,%s,%s)))) 
                    GROUP BY CANCHA.CANCHA_PK
                    ''',(day.strftime("%Y-%m-%d"),INDEX,PRE_PK,day.strftime("%Y-%m-%d"),day.strftime("%Y-%m-%d"),INDEX,flag[tipo][0],flag[tipo][1],flag[tipo][2],flag[tipo][3]))
        res += [dict((cur.description[i][0], value) for i, value in enumerate(row)) for row in cur.fetchall()]
    cur.close()
    return res