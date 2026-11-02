# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Java interview programs (clone of github.com/rangareddy/JavaInterviewPrograms, branch `master`). Each program is a standalone class under `src/main/java/com/ranga/<category>/` (`array`, `basic`, `dp`, `search`, `sort`, `string`, `threads`). There is no shared code between classes, no test suite, and no external dependencies.

Some programs also have a write-up in `md_files/<category>/`. `README.md` is the index: programs are grouped by category and link to either the `.md` write-up or the `.java` source. When adding or moving a program, update both the package and the README entry.

## Build and run

```
mvn compile                                        # compile into target/classes
java -cp target/classes com.ranga.array.TwoSum     # run one class by its fully qualified name
```

Compiler level is set via `maven.compiler.source`/`target` (8) in `pom.xml`; keep code Java 8 compatible. There are no tests or lint configuration. Each class is exercised by its own `public static void main` with hard-coded inputs.

## Workflow

Changes go through a feature branch and PR against `master` (merged with merge commits), not direct pushes.
