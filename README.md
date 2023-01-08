# notch-shell-utils

[![CI Build](https://github.com/wearenotch/notch-shell-utils/actions/workflows/ci.yml/badge.svg)](https://github.com/dmadunic/gh-demo-lib/actions/workflows/ci.yml)
![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white&style=flat)

Library containg various shell utils.

**Current project version 0.1.2**

## Project dependencies

| Dependency               | Version |  
| ------------------------ | ------- |
| de.vandermeer:asciitable | 0.3.2   |
| org.jline:jline          | 3.21.0  |
| com.fasterxml.jackson.core:jackson-databind | 2.13.4.2 |


## Usage
To use this jar in your project add the following to the dependencies section:

```groovy
dependencies {
    implementation "com.notch.utils:notch-shell-utils:0.1.2"
    ...
}
```
(build.gradle)

```xml
<dependency>
  <groupId>com.notch.utils</groupId>
  <artifactId>notch-shell-utils</artifactId>
  <version>0.1.2</version>
</dependency>
```
(pom.xml)

And regitser github package as maven repository, as is for example show in the snippet bellow:

```groovy
def props = new Properties()
if (file(".env").exists()) {
    file(".env").withInputStream { props.load(it) }
}

repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/wearenotch/notch-shell-utils")
        credentials {
            username = System.getenv("gh_username") ?: props.getProperty("gh_username")
            password = System.getenv("gh_token") ?: props.getProperty("gh_token")
        }
    }
}
```

**gh_token** should contain the value of (PAT) "personal access token" that has Access to public repositories.
You can store this value in (1) .env file (do not forget to add this file to .gitignore!) or (2) configure system environment variable to save it.
First option is the simplest for local development, while the second is more suitable for ci/cd workflows.

For more see:
* [Personal GitHub access tokens](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
* [Working with the Gradle registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry)

## Shell Tools

### Working with text output:
- Chalk: utility to display text in colors
```Java
System.out.println(Chalk.red("This message will be displayed in RED and bolded", true));
```

- ShellHelper: utility that allows display of contextual messages (info, success, warn, error)
```Java
org.jline.terminal.Terminal terminal = org.jline.terminal.TerminalBuilder.builder().build();

ShellHelper sh = new ShellHelper(terminal);
sh.printlnInfo("This will be printed in the color configured for INFO messages (default is cyan))");
```

### Tracing progress

- ProgressBar
- ProgressCounter

### Capturing user input
- InputReader - util class to ask user for input

To construct instance of the InputReader run either:
```Java
InputReader reader = new InputReader(lineReader, shellHelper, "yellow", "*");
```
or
```Java
InputReader reader = new InputReader(lineReader, shellHelper);
```
The later will use **cyan** as default color for info messages and **'*'** for mask when capturing sensitive input (ie. credentials)

LineReader useed in constructor of the InputReader can be constructed for example by running the following commands:
```Java
org.jline.reader.History history = new org.jline.reader.impl.history.DefaultHistory;();
org.jline.reader.DefaultParser parser = new DefaultParser();
parser.setEofOnUnclosedQuote(true);
parser.setEofOnEscapedNewLine(true);

LineReaderBuilder lineReaderBuilder = LineReaderBuilder.builder()
    .terminal(terminal)
    .history(history)
    .highlighter(new Highlighter() {

        @Override
        public AttributedString highlight(LineReader reader, String buffer) {
            return new AttributedString(
                buffer,
                AttributedStyle.BOLD.foreground(AttributedStyle.WHITE)
            );
        }
                
        @Override
        public void setErrorPattern(Pattern errorPattern) {
        }

        @Override
        public void setErrorIndex(int errorIndex) {
        }
    }).parser(parser);

LineReader lineReader = lineReaderBuilder.build();
lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
```

For more details on how to construct Histroy, Parser, Completer and Highliter see official [jLine docs](https://github.com/jline/jline3/wiki).

#### Available methods:

1. Capture of free user input:
```Java
reader.prompt("Enter some text:")
```
or
```Java
reader.prompt("Enter some text:", "Default Value to return if user does not enter anything")
```

2. Capture sensitive data (credentials)
```Java
reader.prompt("Enter password", null, false)
```

3. Prompt for one of options
Loops until one of the `options` is provided. Pressing return is equivalent to returning `defaultValue`.

Passing null for defaultValue signifies that there is no default value.

Passing "" or null among optionsAsList means that empty answer is allowed, in these cases this method returns empty String "" as the result of its execution.

For example the following will prompt user to enter one of the following: 'a', 'b', 'c' or 'q' with 'a' being returned as default value.
```Java
List options = Arrays.asList(new String[]{"a", "b", "c", "q"}); 
reader.promptWithOptions("Enter one of the values (a*,b,c, q)", "a", options);
```

4. Prompt user to select one value from the list of options, printing each option on its own line

```Java
reader.selectFromList(String headingMessage, String promptMessage, Map<String, String> options, boolean ignoreCase, String defaultValue);
```


### Tables
- Display entity as table
- Display table with one entity per row


For detailed overview of how to use various tools from this library see: [](piccocli-shell-utils-demo) project. 

## Development
### Setup (First time)
1. Clone the repository: `git clone git@github.com:wearenotch/notch-shell-utils`
4. Build project with: ` ./gradlew clean build `

### Github Actions release

New version of library jar  is released with the "Release next version" github action script.
Once everything is pushed simply run this workflow script.
It will perform the following:
- build new jar version
- publish jar to github packages
- Create new Relase with comments from CHANGELOG.md for this release.
- Update README.md to contain latest version number.

### Manual Release
Make sure that file gradle.properties in the folder ${USER_HOME}/.gradle/ contains the following two variables defined:

* github_username
* github_password : personal github token to be used to install/update packages

1) Commit and push everything
2) `./gradlew release`

And simply follow the instructions on the console

