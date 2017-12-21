import itertools

somelists = [
    ['-', '|', '/', '\\'],
    ['-', '|', '/', '\\'],
    ['-', '|', '/', '\\']
]

for element in itertools.product(*somelists):
    print(' * '.join(element))
