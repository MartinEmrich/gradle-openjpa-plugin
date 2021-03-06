package com.neva.gradle.plugin.openjpa.task

import org.gradle.api.file.FileCollection
import org.gradle.api.internal.AbstractTask
import org.gradle.api.tasks.TaskAction

class EnhanceTask extends AbstractTask {

    static final NAME = "enhanceEntities"

    def includeFilter = {
        include '**/*Entity.class'
    }

    EnhanceTask() {
        description = "Enhance JPA entity classes using OpenJPA Enhancer"

        configure {
            inputs.dir(inputDir)
            dependsOn([project.compileJava, project.processResources])
            project.classes.dependsOn this
        }
    }

    @TaskAction
    def run() {
        // define the entity classes
        def entityFiles = project.fileTree(inputDir).matching(this.includeFilter)

        println "Enhancing with OpenJPA, the following files..."
        entityFiles.getFiles().each {
            println it
        }

        // define Ant task for Enhancer
        ant.taskdef(
                name: 'openjpac',
                classpath: project.sourceSets.main.runtimeClasspath.asPath,
                classname: 'org.apache.openjpa.ant.PCEnhancerTask'
        )

        // Run the OpenJPA Enhancer as an Ant task
        //   - see OpenJPA 'PCEnhancerTask' for supported arguments
        //   - this invocation of the enhancer adds support for a default-ctor
        //   - as well as ensuring JPA property use is valid.
        ant.openjpac(
                classpath: project.sourceSets.main.runtimeClasspath.asPath,
                addDefaultConstructor: true,
                enforcePropertyRestrictions: true
        ) {
            entityFiles.addToAntBuilder(ant, 'fileset', FileCollection.AntType.FileSet)
        }
    }

    def getInputDir() {
        return project.sourceSets.main.output.classesDirs
    }

}
