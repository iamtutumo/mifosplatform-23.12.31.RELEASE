#!/bin/sh
set -e

TEMPLATE="/usr/share/nginx/html/assets/env.template.js"
TARGET="/usr/share/nginx/html/assets/env.js"

if [ -f "$TEMPLATE" ]; then
  echo "Generating env.js from template"
  envsubst < "$TEMPLATE" > "$TARGET"
fi

exec "$@"
