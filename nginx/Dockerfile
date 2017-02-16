FROM nginx

COPY vhost.conf /etc/nginx/conf.d/
COPY syf-ui /opt/webapp

RUN chown nginx:nginx /opt/webapp \
 && chmod -R 644 /opt/webapp \
 && chmod 644 /etc/nginx/conf.d/vhost.conf

