FROM nginx:alpine
COPY dist/frontend/browser /usr/share/nginx/html
COPY docker/nginx.conf /etc/nginx/conf.d/default.conf

COPY src/assets/env.template.js /usr/share/nginx/html/assets/env.template.js
CMD ["/bin/sh", "-c", "envsubst < /usr/share/nginx/html/assets/env.template.js > /usr/share/nginx/html/assets/env.js && exec nginx -g 'daemon off;'"]

EXPOSE 80
