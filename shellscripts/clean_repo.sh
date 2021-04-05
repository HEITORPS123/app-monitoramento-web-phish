#!/bin/bash

find /home/heitor/Projetos/aplicacao_urls/urls/repo/ -type f -mtime +3 -exec rm -f {} \;
