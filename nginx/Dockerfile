FROM nginx

COPY vhost.conf /etc/nginx/conf.d/
COPY syf-ui /opt/webapp

RUN chown -R nginx:nginx /opt/webapp \
 && chmod -R 644 /opt/webapp \
 && find /opt/webapp -type d -exec chmod +x {} \; \
 && chmod 644 /etc/nginx/conf.d/vhost.conf

