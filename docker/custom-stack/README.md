Quick custom stack using images pushed to Docker Hub (etumoses)

Prereqs:
- Docker & docker compose installed
- Ports 3310, 9080, 9090, 8081 free on host

Start stack:
```bash
cd docker/custom-stack
docker compose up -d
```

Stop and remove:
```bash
docker compose down -v
```

Notes:
- MariaDB data will be stored in `mariadb/data` under this folder.
- The web app will be available at http://localhost:8081 and will call the backend at `http://fineract-server:8080` inside the compose network.
- If you want the web UI to use an external backend URL (e.g. GitHub Codespace tunnel), edit the `FINERACT_API_URLS` / `FINERACT_API_URL` env vars in `docker-compose.yml`.
