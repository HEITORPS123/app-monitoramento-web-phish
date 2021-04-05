#!/bin/bash

echo "started $(date)" >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
FIREFOX_PID=$(ps aux | grep firefox | grep -v grep | awk '{print $2}')
MAIN=$(ps aux | grep main.py | grep -v grep | awk '{print $2}')
echo "--process-alive--" >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
echo $FIREFOX_PID  >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
echo $MAIN  >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
#echo $SENDMAIL  >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
sudo kill -9 $FIREFOX_PID
sudo kill -9 $MAIN
#sudo kill -9 $SENDMAIL
echo "--process-pos-clean--"  >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
TPID=$(ps aux | grep tcpdump | grep -v grep | awk '{ print $2}')
FIREFOX_PID=$(ps aux | grep firefox | grep -v grep | awk '{ print $2}')
#SENDMAIL=$(ps aux | grep sendmail | grep -v grep | awk '{print $2}')
echo $FIREFOX_PID  >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
echo $MAIN  >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
echo "--xxxxxxx--"  >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
echo "finished $(date)" >>  /home/ufmg_cipops/precompiled_packets/urls/clean_mem.log
