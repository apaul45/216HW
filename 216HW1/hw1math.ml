(* Ayon Paul
   CSE 216 (Recitation 3)
   HW 1 Part 2, Questions 2 and 3 (hw1math.ml) *)

(* Question 2 *)
type expr = Const of int |Var of string|Mult of args|Minus of args|Plus of args
          |Div of args and args = {arg1: expr; arg2: expr};;
(* Question 3 *)
let rec evaluate (expr) = match expr with
  |Const c1-> c1
  |Var c1 -> 0
  |Mult {arg1;arg2} -> evaluate(arg1)*evaluate(arg2)
  |Div {arg1;arg2} -> evaluate(arg1)/evaluate(arg2)
  |Plus{arg1;arg2} -> evaluate(arg1)+evaluate(arg2)
  |Minus {arg1;arg2} -> evaluate(arg1)-evaluate(arg2);; 