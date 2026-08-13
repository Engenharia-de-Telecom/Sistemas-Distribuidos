FROM python:3.14-slim

WORKDIR /app
# Diretório de trabalho

RUN pip install flask redis
# redis é um banco de dados de chaves e valores

ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0
ENV PYTHONUNBUFFERED=1
# Variáveis de ambiente pedidas pelo FLASK
# Caso  não seja declarado as variáveis de ambiente, ele assume valores padroẽs

EXPOSE 5000
# Porta que o container vai ouvir

CMD [ "flask", "run" ]
