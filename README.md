# freezing-hipster

Downloads historic weather data through wunderground.com's API and outputs the results to a CSV file.

Currently outputs:
* max humidity
* min humidity
* max temperature
* min temperature
* mean barometric pressure

## Installation

Clone the source and run:

    $ lein uberjar

## Usage

    $ java -jar freezing-hipster.jar KEY LOCATION FROM-DATE TO-DATE OUT-FILE

You need a key from wunderground.com to run this.

## Examples

    $ java -jar freezing-hipster.jar 0123456789abcdef TX/Austin 20130528 20130529 out.csv
    $ java -jar freezing-hipster.jar 0123456789abcdef 73301 20130528 20130529 out.csv
    
## TODO
* More output data
* More output formats
    * JSON
    * XML
    * Google Drive
* Alternate weather sources
    * http://www.almanac.com
    * http://www.intellicast.com
    * ftp://ftp3.ncdc.noaa.gov/pub/data/noaa/
    * http://graphical.weather.gov/xml/

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
