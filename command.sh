#!/bin/bash
file=${HOME}/oldData.log
i=0
j=0
command ()
{
  while read Line ; do
    #statements

    for var in $Line;do
      Array[i]=$var
      first[j]=${Array[1]}
      second[j]=${Array[3]}
      ((i++))
    done

[[ "${first[j]}" =~ ^[+-]?[[:digit:]]+$ ]] && echo ${first[j]} ||  first[j]=0
[[ "${second[j]}" =~ ^[+-]?[[:digit:]]+$ ]] && echo ${second[j]} ||  second[j]=0
var1=${first[j]}
var2=${second[j]}
#sum1=((var1+var2))
echo "$var1 .. $var2 => Sum is $(($var1+$var2))"
i=0
((j++))
  done < $1
}


command "$file"
echo ${first[*]} $'\n' ${second[*]} >newData.log
echo ${second[6]}
