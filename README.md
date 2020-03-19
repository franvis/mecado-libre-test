# mercado-libre-test

This project provides a pre commit hook that runs both detekt and spotlessCheck to
avoid code smells, format the code and run ktlint for code styling

To enable it run in the root of your project:
`git config core.hooksPath .githooks`
