# Conventions

## Branch

There are two continuous branches:

- `main`
- `dev`

### Branch name

```txt
<type>/<issue number>_<issue name>
```

where **type** is `feat`, `fix`, `docs`, `test` or `refactor`.

For example, `feat/42_awesome_feature`.

### Branch Creation

All branches need to be created **from `dev`** and associated with an issue.

## Commit

### Commit name

```txt
<type> : <description> (#<issue number>)
```

where **type** is `feat`, `fix`, `docs`, `test` or `refactor`.

Characters limit: about 50 characters

For example, `fix: indentation (#42)`.

## Issue

An issue needs

- to be associated:
    - with a **contributor**
    - with a descriptive **label**
- to have:
    - a concise but descriptive **name**
    - a precise **description** of changes

## PR

PR must have at least two reviewers and these must express themselves respectfully. Emojis' are encouraged. 😄

## Code conventions

Contributors should refer to the [Clean code](https://gist.github.com/wojteklu/73c6914cc446146b8b533c0988cf8d29) book for good code quality development.

- Line width: 120 characters

- Naming:
    - Classes: PascalCase
    - Interfaces: PascalCase (don't put an "I" in front of it)
    - Methods: camelCase
    - Variables: camelCase
    - Final constants: SCREAMING_SNAKE_CASE
    - Global constants: SCREAMING_SNAKE_CASE
