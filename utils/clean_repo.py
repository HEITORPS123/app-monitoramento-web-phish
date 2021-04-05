import os
import datetime

path = "/home/heitor/Projetos/aplicacao_urls/urls/repo"
hoje = datetime.datetime.now()
data = hoje.strftime('%Y%m%d')
print(data)
data_atual = int(data)
lista_arquivos = os.listdir(path)
print(lista_arquivos)
for arquivo in lista_arquivos:
    dif = data_atual - int(arquivo)
    if dif >= 3:
        #print("Mais de 3 dias") 
        os.system("rm "+path+"/"+arquivo)