import os

lista = list()
for arq in os.listdir('.'):
    lista.append(arq[:8])

for pref in set(lista):
    print(pref)
