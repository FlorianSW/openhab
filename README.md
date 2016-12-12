
## Introduction

THIS IS NOT WHAT YOU'RE LOOKING FOR!
This repository is just a fork of the official openHAB repository and is used for some small, maybe instable modifications to openHAB (the 1.x release) and the openHAB designer for special and specific use cases. If you would like to use the current openHAB stable version or if you want to contribute to openHAB, please refer to the official repository: https://github.com/openhab/openhab-distro/blob/master/CONTRIBUTING.md

## Changes

The following list should be a list of changes made to this repository since it was forked from the official openHAB repository. Changes were made to the 1.8 branch only (which is the default branch here). An up-to-date list of changes/commits can be found here: https://github.com/openhab/openhab/compare/1.8...FlorianSW:1.8

* [openHAB designer] The configuration files (items, rules, sitemaps, ...) are automatically reloaded into the left "Configurations" panel, if there were changes to the files or file structure ([57fd8b3](https://github.com/openhab/openhab/commit/57fd8b34b01a209a44ffe3eeca0aadb4fd4ea7d6))
* [openHAB designer] "New file" UI added, which allows the user to create files from inside the designer ([53509c2](https://github.com/openhab/openhab/commit/53509c2150e7ecfaf719987055a187dc1ee18fbd))
* [openHAB designer] "New item" wizard, which allows a user to use a wizard to create a new items file with a template of a single item ([aa5c2b4](https://github.com/openhab/openhab/commit/aa5c2b422c4bb2e830a1ef8fc20d8efc976e71c4))
* [openHAB rules] The new variable "receivedCommandItem" holds the item, which triggered the current run of the rule and can be used to directly access the item (useful when multiple items could trigger a rule, connected with or, but a general action should be done to a specific item or to another item, which is selected based on the triggering item). ([6723ab1](https://github.com/openhab/openhab/commit/6723ab135149d41b39fee425e880e7cccf7cc85c))

## Trademark Disclaimer

Product names, logos, brands and other trademarks referred to within the openHAB website are the property of their respective trademark holders. These trademark holders are not affiliated with openHAB or our website. They do not sponsor or endorse our materials.
