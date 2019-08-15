# robots-vs-dinosaurs

## Start Data Base

Starter postgres in docker
```
docker run --name some-postgres -e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=robots_vs_dinosaurs -d -p 5432:5432 postgres
```

Run Flyway
```
lein flyway migrate
```

## Usage

Create new grid

```
curl --request POST \
  --url http://localhost:8080/grids
```

Create new robot

```
curl --request POST \
  --url http://localhost:8080/robots \
  --header 'content-type: application/json' \
  --data '{"grid-id": 1,"pos-x": 1,"pos-y": 0,"direction": "E"}'
```

Create new dinosaur

```
curl --request POST \
  --url http://localhost:8080/dinosaurs \
  --header 'content-type: application/json' \
  --data '{"grid-id": 1,"pos-x": 4,"pos-y": 1}'
```

Send instruction for robot

```
curl --request PUT \
  --url http://localhost:8080/robots/1/instructions \
  --header 'content-type: application/json' \
  --data '{"action": "move-forward"}'
```

Display status

```
curl --request GET \
  --url http://localhost:8080/grids/1
```

## License

Copyright © 2019 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
