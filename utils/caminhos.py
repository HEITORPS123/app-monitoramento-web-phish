import time
import datetime
import json
import socket

def criar_caminhos(): #Cria o arquivo json que contem os caminhos dos logs.
    ts = time.time()
    delay = datetime.timedelta(seconds=3600)
    hoje = datetime.datetime.now() - delay
    data = hoje.strftime('%Y%m%d%H%M%S')
    hostname = socket.gethostname()
    dicionario = {}
    dicionario['http_log'] = data+".http."+hostname
    dicionario['http_exception'] = data+".http_exception."+hostname
    dicionario['recip'] = data+".recip."+hostname
    dicionario['source_page'] = data+".source_page."+hostname
    dicionario['firefox_exception'] = data+".firefox_exception."+hostname
    dicionario['access_log'] = data+".access_log."+hostname
    dicionario['tcp'] = data+".tcp."+hostname
    dicionario['time_urls'] = data+".time_urls."+hostname
    dicionario['time'] = data+".time."+hostname
    dicionario['files_made'] = data+".files_made."+hostname
    dicionario['cadeia_urls'] = data+".cadeia_urls."+hostname
    dicionario['inicio'] = data+".inicio."+hostname
    #print(dicionario)
    with open("caminhos.json","w") as fw:
        json.dump(dicionario,fw)

def config_paths(): #Abre o arquivo caminhos.json.
    with open("caminhos.json", "r") as fr:
        out_str = fr.read()
    out_dict = json.loads(out_str)

    return out_dict

def recebe_data():
    hoje = datetime.datetime.now()
    data = hoje.strftime('%Y %m %d %H:%M:%S')
    return data
