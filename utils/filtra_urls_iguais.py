import glob
import os
from datetime import datetime

basedir = "/home/tlhop/aplicacao_urls/urls/repo"
dataatual = datetime.today().strftime('%Y%m%d')
conjunto_urls = set()
lista_arquivos = glob.glob(basedir+"/*")
for arquivo in lista_arquivos:
    fr = open(arquivo,'r')
    urls = fr.readlines()
    for url_data in urls:
        url_data = url_data.replace('\n','')
        url,data = url_data.split("  ")
        conjunto_urls.add(url)
    fr.close()
    os.remove(arquivo)

fw = open(basedir+"/urls_acesso","w")
for url in conjunto_urls:
    linha = url + "  " + dataatual + "\n"
    fw.write(linha)
fw.close()