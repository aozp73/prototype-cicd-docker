FROM mongo:5.0

COPY ./00-db-setting.sh /docker-entrypoint-initdb.d/
COPY ./01-data-setting.js /docker-entrypoint-initdb.d/

RUN chmod 755 /docker-entrypoint-initdb.d/00-db-setting.sh

RUN echo "net:\n  port: 27019" > /etc/mongod.conf
EXPOSE 27019

CMD ["mongod", "-f", "/etc/mongod.conf"]
