tbh I cheated here, I don't have the time right now to think myself into the problem
and I've already solved it (a few years prior).

I've gotten my shortened solution from here:
https://github.com/dp1/AoC17/blob/master/day23.5.txt

```python
from sympy import isprime

b = 109300
c = 126300

h = 0

for v in range(b, c + 1, 17):
  if not isprime(v):
    h += 1
```

The resulting `h` will be 911
