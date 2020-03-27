# Tanach Markov Chains

A program which generates Tanach style verses using markov chains.

## Data Preparation

The data for the markov chain is collected from [Sefaria](https://sefaria.org), specifically [Sefaria Export](https://github.com/Sefaria/Sefaria-Export) - a GitHub repo containing raw data for all of Tanach.

The `TanachDataPreperation` class along with the `TanachBook` enum is used to download the data from Sefaria and to perform some basic cleanup, removing titles and empty lines.

## The Markov Model

The model is a first order markov model. It parses every verse in tanach to calculate the probability of any word following any other word and saves this into a
`Map<String, Map<String, Double>>` object.

Sentence start and ends are encoded as words since the probability of a given word appearing at the start / end of a verse is different.

This model can then be used to generate random verses in the style of tanach verses.

## Usage

TanachDataPreparation.java will download all the data from sefaria and then perform some basic cleanup on it.
MarkovModel.java can either directly generate verses, writing them to stdout or can save the markov model as a JSON file to an output directory. To generate a certain number of verses simply supply the number of verses as a command line argument. To save the model supply `save` as a command line argument. For example
`java MarkovModel.java 10` will generate 10 verses and
`java MarkovModel.java save` will save the model.

The website works using the JSON model. It first loads in the object and then generates verses using it.
