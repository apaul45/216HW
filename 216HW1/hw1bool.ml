(* Ayon Paul
   CSE 216 (Recitation 3)
   HW 1 Part 2.1 (hw1bool.ml)*)
(* Question 1 *)
type bool_expr =
  | Lit of string
  | Not of bool_expr
  | And of bool_expr * bool_expr
  | Or of bool_expr * bool_expr;;
let tupleHead tuple = match tuple with
  |(a,_,_) -> a;;
let truth_table lit1 lit2 litexp = let rec ttHelper lit1 lit2 litexp c = match
                                       litexp with
                                   |And (exp1, exp2) -> if c=0 then let value1 = tupleHead(List.hd (ttHelper lit1 lit2 exp1 (c+1)))
                                         in let value2 = tupleHead(List.hd (ttHelper lit1 lit2 exp2 (c+1)))
                                         in [(value1, value2, value1 && value2);
                                             (value1, not value2, value1 &&  not value2);
                                             (not value1, value2, not value1 && value2);
                                             (not value1, not value2, not value1 && not value2)] 
                                       else
                                         let val1 = tupleHead(List.hd (ttHelper lit1 lit2 exp1 (c+1))) in
                                         let val2 = tupleHead(List.hd (ttHelper lit1 lit2 exp2 (c+1))) in
                                         [(val1 && val2,val1 && val2,val1 && val2)]
                                   |Or (exp1, exp2) ->if c=0 then let value1 = tupleHead(List.hd (ttHelper lit1 lit2 exp1 (c+1)))
                                         in let value2 = tupleHead(List.hd (ttHelper lit1 lit2 exp2 (c+1)))
                                         in [(value1, value2, value1 || value2);
                                             (value1, not value2, value1 || not value2);
                                             (not value1, value2, not value1 || value2);
                                             (not value1, not value2, not value1 || not value2)] 
                                       else
                                         let val1 = tupleHead(List.hd (ttHelper lit1 lit2 exp1 (c+1))) in
                                         let val2 = tupleHead(List.hd (ttHelper lit1 lit2 exp2 (c+1))) in
                                         [(val1 || val2, val1 || val2, val1 || val2)]
                                   |Not (exp1) -> let val1 = tupleHead(List.hd(ttHelper lit1 lit2 litexp (c+1)))
                                       in [(not val1, not val1, not val1)]
                                   |Lit (lit1) -> [(true, true, true)] in
  ttHelper lit1 lit2 litexp 0;;