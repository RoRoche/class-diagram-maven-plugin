final File diagram = new File(basedir, 'target/class-diagrams/example.puml')
assert diagram.isFile()
assert diagram.getText('UTF-8').contains('Example')
