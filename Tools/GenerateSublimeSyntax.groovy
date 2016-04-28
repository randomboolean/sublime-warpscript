//
//   Copyright 2016  Cityzen Data
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//

import groovy.json.JsonSlurper
import groovy.json.JsonOutput

// Read json file 
File warpscriptFunctions = new File("./warpscript-functions.json")
def jsonSlurper = new JsonSlurper()
def functions = jsonSlurper.parse(warpscriptFunctions)

def String regexpEscape(String input) {
  input = input.replace('\\', '\\\\')
  input = input.replace('^', '\\^')
  input = input.replace('$', '\\$')
  input = input.replace('.', '\\.')
  input = input.replace('|', '\\|')
  input = input.replace('?', '\\?')
  input = input.replace('*', '\\*')
  input = input.replace('+', '\\+')
  input = input.replace('[', '\\[')
  input = input.replace(']', '\\]')
  input = input.replace('(', '\\)')
  input = input.replace(')', '\\)')
  input = input.replace('{', '\\}')
  input = input.replace('{', '\\}')  
  return input
} 

// load template
def syntaxFileTemplate = new File('./mc2.sublime-syntax.template').getText('UTF-8')

// completion output
def completionOutput = []


functions.each { function ->    
  switch(function.key) {
    case 'constants':
      syntaxFileTemplate = syntaxFileTemplate.replace('{{constants}}', function.value.join('|'))

      // Adds completion output
      completionOutput.addAll(function.value)
    break;

    case 'frameworks':
    // MERGE frameworks functions
    def frameworkFunctions = []
    function.value.each { k,v ->
      frameworkFunctions.add(k)
      v.each { categorie, values ->
        frameworkFunctions.addAll(values)

        // Adds completion output
        completionOutput.addAll(values)
      }      
    }
    syntaxFileTemplate = syntaxFileTemplate.replace("{{functions_frameworks}}", frameworkFunctions.join('|'))
    
    break;

    case 'functions':
      function.value.each { name, values ->
        name = name.replace('.','_')
        syntaxFileTemplate = syntaxFileTemplate.replace("{{$name}}", values.join('|'))

        // Adds completion output
        completionOutput.addAll(values)
      }
    break;

    case 'operators':
      function.value.each { name, values ->
        name = name.replace('.','_')

        def operators = values.collect { it ->
          it = regexpEscape(it)
        }.join('|')

        syntaxFileTemplate = syntaxFileTemplate.replace("{{$name}}", operators)

        // Adds completion output
        completionOutput.addAll(values)
      }
    break;

    case 'structures':
      def structures = function.value.collect{ it ->
        // Removes manueally <' and '> (static code in the template)
        if ("<'".equals(it) || "'>".equals(it)) {
          return
        }
        it = regexpEscape(it)
      }.join('|')

      syntaxFileTemplate = syntaxFileTemplate.replace('{{structures}}', structures)

      // Adds completion output
      completionOutput.addAll(function.value)
    break;    
  }
}

// save syntax file
def outputFile = new File('./mc2.sublime-syntax')
outputFile << syntaxFileTemplate

def outputCompletionFile = new File('./mc2.sublime-completions')
outputCompletionFile << JsonOutput.toJson([scope: 'source.mc2', completions: completionOutput])




