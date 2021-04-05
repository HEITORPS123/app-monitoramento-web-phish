BASEDIR=/home/tlhop/aplicacao_urls
BASEEXEC=$BASEDIR/urls
BASELOGS=$BASEEXEC/finallogs
BASEUTILS=$BASEEXEC/utils
TESTEDIR=$BASEEXEC/teste
TESTELOGS=$TESTEDIR/finallogs_teste

dataatual=$(date +%Y%m%d)

cd $BASEEXEC

# Anuciando que uma nova inst�ncia da APP vai ser iniciada nos proximos instantes.
# marcando como 0 o valor do arquivo operante.

echo "0" > shellscripts/sys/operante
echo "1" > shellscripts/sys/usa_recursos

while [ $(cat shellscripts/sys/novas_urls) -eq 0 ];
do
        sleep 60
        echo "Aguardando a instancia ativa terminar"
done

echo "0" > shellscripts/sys/novas_urls

# Caso tenha uma instancia da aplicacao ativa netes momento espere ate ela terminar
while [ $(cat shellscripts/sys/esperar) -eq 1 ];
do
	sleep 10
	echo "Aguardando a instancia ativa terminar"
done

# Como a instancia que estava rodando terminou, aloque o processamento para a
# a instancia atual. Para isso marque os arquivos com 1.

echo "1" > shellscripts/sys/operante
echo "1" > shellscripts/sys/esperar

# Execute a aplicacao.

mkdir -p logs finallogs

PATH=$PATH:${BASEDIR}/env/bin
python3 utils/filtra_urls_iguais.py
java -jar App.jar 5 15 acesso 1800 60

# Envie os logs para a koloth.

cd $BASELOGS

for prefixo in $(python3 $BASEUTILS/prefixo.py);
do
	ano=${prefixo:0:4}
	mes=${prefixo:4:2}
	dia=${prefixo:6:2}
	dst="$ano/$mes/$dia"
	ssh nomeservidor mkdir -p /var/spammining1/phishing/producao/logs_repo/$dst
	rsync --remove-source-files $prefixo* nomeservidor:/var/spammining1/phishing/producao/logs_repo/$dst
done
# Liberando o recurso do servidor para a proxima instancia.

cd $BASEEXEC

echo "0" > shellscripts/sys/esperar
echo "0" > shellscripts/sys/operante
echo "0" > shellscripts/sys/usa_recursos

cd /tmp/
TMPDIR=$(pwd)
if [ $TMPDIR == "/tmp" ]
	then ls | xargs -n 1000 rm -rf
fi

# Limpando a mem�ria do sistema.
python3 /home/tlhop/aplicacao_urls/urls/utils/clean_repo.py
