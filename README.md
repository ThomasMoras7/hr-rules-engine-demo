# HR Rules Engine

Moteur de règles RH Java 21. Calcule les jours de congés à partir d'un solde de base plus des jours supplémentaires liés à l'ancienneté. Les règles sont pilotées par la donnée (`config/rules.json`) : modifier un paramètre ne nécessite aucun changement de code.

## Prérequis

- JDK 21 ou plus récent
- Aucun : Maven n'a pas besoin d'être installé, le wrapper `mvnw.cmd` s'en charge (téléchargement local au premier lancement).

## Structure

```
├── config/rules.json          # règles (jours de base + ancienneté)
├── docs/                      # documentation
├── src/main/java/com/hr/rulesengine/
│   └── Main.java              # point d'entrée (toute la logique, proto)
└── pom.xml
```

## Commandes

```sh
mvnw.cmd compile          # compiler
mvnw.cmd test             # lancer les tests
```

## Règles actuelles

- 25 jours de congés de base
- Ancienneté → jours supplémentaires :

| Ancienneté | Jours supplémentaires |
| --- | --- |
| 10 ans | 1 |
| 15 ans | 2 |
| 20 ans | 3 |
| 25 ans | 4 |

La documentation complète se trouve dans [`docs/`](docs/Index.md).
