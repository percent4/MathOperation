import sys

oper = sys.argv[1]

try:
	print(eval(oper))
except Exception as e:
    print('Error:', end='')
    print(e)