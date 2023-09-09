FROM mysql:8.0

COPY ./00-db-setting.sh /docker-entrypoint-initdb.d/
COPY ./01-table-setting.sql /docker-entrypoint-initdb.d/
COPY ./02-data-setting.sql /docker-entrypoint-initdb.d/
COPY ./parameter.conf /etc/mysql/conf.d/my.cnf

RUN sed -i 's/\r$//' /docker-entrypoint-initdb.d/00-db-setting.sh
RUN chmod 644 /etc/mysql/conf.d/my.cnf
RUN chmod 755 /docker-entrypoint-initdb.d/00-db-setting.sh

EXPOSE 3306