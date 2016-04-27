# Warp Script Package for Sublime Text 3

## Info

Warp Script is a time series data manipulation language for [Warp10](http://www.warp10.io) platform. This packages enables syntax coloration for WarpScript files (.mc2 extension) and snippets for WarpScript's frameworks (MAP, REDUCE, FILTER, BUCKETIZE, APPLY) and conditional instructions (IFT, IFTE)

## Version

This package user have a x.y.z-indice version number.
x.y.z is the base version of Warp10 pltaform, the "indice" is the package build number. This link is mandatory because Warp Scripts functions list is directly extracted from [Warp10-plateform](https://github.com/cityzendata/warp10-platform)

## Known limits 

Invalid expressions likes `LIST->DROP` are not yet detected as invalid syntax (a space is missing before DROP). This package has been based on `\b\b` regexp witch is not fully adapted to WarpScript's syntax. I'll fix it in a future version, with helps of a regexp craftman ;-)

## Install

The easiest way to install this is with Package Control.

 * Bring up the Command Palette (Command+Shift+p on OS X, Control+Shift+p on Linux/Windows).
 * Select "Package Control: Install Package" (it'll take a few seconds)
 * Select WarpScript when the list appears.

Package Control will automatically keep the package up to date with the latest version.

## Issues & Feature Requests

Please use [GitHub Issue Tracker](https://github.com/cityzendata/sublime-warpscript/issues) to report any bugs and make feature requests.

## Licensing

Licensed under Apache 2 license (see http://www.apache.org/licenses/LICENSE-2.0)
