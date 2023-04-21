To execute scripts from command line
> mvn clean test -Dtest=Executor.TestRunner#testExecutor
> mvn clean test -Dtest=packageName.className#methodName

To execute scripts from command line and pass groups 
> mvn clean test -Dtest=Executor.TestRunner#testExecutor -DtestGroup=basics test
> mvn clean test -Dtest=packageName.className#methodName -DnameofParameter=groupsMentioned test