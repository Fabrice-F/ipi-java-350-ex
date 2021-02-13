# EVALUTATION
   `Commencer par faire une branche `evaluation` à partir de votre branche `master` une fois le TP terminé. Travailler sur cette branche pour l'évaluation.`

## (1) OK : Tests unitaires et TDD augmenterSalaire

- Tester de manière unitaire le plus exhaustivement possible la méthode `augmenterSalaire` d'`Employe` en essayant de faire du TDD. Décommenter la méthode dans `Employe` et écrire d'abord les tests entièrement (en réflechissant particulièrement aux cas limites) avant d'écrire la méthode. Pensez-vous que vous auriez écrit la méthode directement comme cela si vous n'aviez pas écrit les tests en premier ?

** Il est clair que non, les cas limites comme la salaire à null ainsi que le salaire avec un nombre de chiffre après la virgule supérieur à 2 n'aurait pas été rélfléchis.**

## (2) : Tester unitairement getNbRtt d'Employe
Tester unitairement (en utilisant les tests paramétrés) la méthode `getNbRtt` d'`Employe`. Le nombre de RTT se calcule à partir de la formule suivante : **Nombre de jours dans l'année - Nombre de jours travaillés dans l'année en plein temps - Nombre de samedi et dimanche dans l'année - Nombre de jours fériés ne tombant pas le week-end - Nombre de congés payés**. Le tout au pro-rata du taux d'activité du salarié. **Attention**, des erreurs sont présentes dans cette méthode. Faites donc vos calculs avant et débugguer votre code pour trouver les erreurs. Aidez-vous de Sonar... Rendre cette méthode plus propre, documentée et lisible.
Infos : 
  - 2019 : l'année est non bissextile, a débuté un mardi et il y a 10 jours fériés ne tombant pas le week-end.
  - 2021 : l'année est non bissextile, a débuté un vendredi et il y a 7 jours fériés ne tombant pas le week-end.
  - 2022 : l'année est non bissextile, a débuté un samedi et il y a 7 jours fériés ne tombant pas le week-end.
  - 2032 : l'année est bissextile, a débuté un jeudi et il y a 7 jours fériés ne tombant pas le week-end.

## (3) Tester sans dépendance calculPerformanceCommercial d'EmployeService
- Tester sans dépendance à la BDD la méthode `calculPerformanceCommercial` d'`EmployeService`

## (4) Tests d'intégration :

- Tester de manière intégrée une cas nominal de la méthode précédente
- Tester de manière intégrée la méthode d'`EmployeRepository` `avgPerformanceWhereMatriculeStartsWith`
- BONUS Ecrire un test d'acceptation Gauge pour la fonctionnalité de calcul de performance d'un commercial.

## (5) Autres :

- S'assurer que votre code passe et qu'il n'y a aucun *code smells* ou *anomalies* ou *bugs* bloquants, critiques ou majeurs. Si c'est le cas, corriger le code fourni.
- S'assurer d'avoir 100% de couverture de code sur les méthodes testés dans l'évaluation. Vérifier la couverture de code avec mutation et à défaut d'atteindre 100%, essayer d'obtenir un bon niveau.

## (6) Revue de code

En fin de TP, créer une Pull Request de votre branche `evaluation` vers `master` et mettez-vous d'accord avec un collègue pour qu'il fasse la revue de code. Faites les éventuelles modifications puis affectez-moi la PR.
