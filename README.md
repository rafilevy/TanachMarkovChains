# Tanach Markov Chains

A program which generates Tanach style verses using markov chains.

## Data Preparation

The data for the markov chain is collected from [Sefaria](https://sefaria.org), specifically [Sefaria Export](https://github.com/Sefaria/Sefaria-Export) - a GitHub repo containing raw data for all of Tanach.

The `TanachDataPreperation` class along with the `TanachBook` enum is used to download the data from Sefaria and to perform some basic cleanup, removing titles and empty lines.

## The Markov Model

The Markov model is then trained with each verse being given as an individual input. Transition probabilities are calculated for each pair of words and stored.

Finally the transition matrix calculated previously can be used to generate Tanach style verses.

## Usage

Running `TanachDataPreparation.java` will download and cleanup all the data from Sefaria for use.

Running `MarkovModel.java` will output a generated Tanach verse to stdout. A number can also be provided as the first command line argument to generate multiple verses for example
`java MarkovModel.MarkovModel 10` will generate 10 verses.