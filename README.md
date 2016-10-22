# Gradle OpenJPA plugin

Plugin for enhancing JPA entity classes at compile time to be ready for OpenJPA. Especially useful when combined with [Javarel Framework](https://github.com/neva-dev/javarel-framework).

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History

* 1.0 - Initial release.

## License

Apache License 2.0

## Usage

By default, plugin enhances all classes that have suffix `Entity`, e.g `UserEntity`, `PostEntity` etc.
But that behavior can be customized to any needs, see below example.

```

apply plugin: 'neva.openjpa'

enhanceEntities {

    includeFilter = {
        include '**/*Entity.class'
    }

}

```