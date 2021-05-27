(* Ayon Paul
   CSE 216 (Recitation 3)
   HW #1 Part 1*)
(*Question 1 *)
let rec pow x y = if y = 0 then 1 else x * pow x (y-1);;
let rec float_pow x y = if y = 0.0 then 1.0 else x *. float_pow x (y-.1.);; 
(* Question 2-- Uses pattern matching and recursion to remove duplicates*)
let rec compress lst = 
  match lst with
  | [x] -> [x]
  | [] -> []
  | first::second::rest -> if first = second then compress (second::rest) else
        first::compress (second::rest);;
(* Question 3-- Uses similar process to question 2 to remove indices that don't
   give a result of true in the inputted predicate*)
let rec remove_if lst predicate =
  match lst with
  | [] -> [] 
  | h::t -> if predicate h = true then remove_if t predicate else h::remove_if t predicate;;
(* Question 4-- checks for invalid numbers first, then uses pattern matching +
  recursion to slice the inputted list *) 
let rec getLength lst = match lst with
  | [] -> 0 
  | [x] -> 1 
  | _::t -> 1+getLength t;;
let rec sliceLoop lst x y sliceCount = match lst with
  | [] -> []
  | [v] -> [v]
  | h::t -> if sliceCount >= x && sliceCount < y then h::sliceLoop t x y (sliceCount+1) 
      else if sliceCount = y then [] else sliceLoop t x y (sliceCount+1);; 
let rec slice lst x y = let counter = getLength lst in 
  if x > counter || x>y then [] else
  if y>counter then sliceLoop lst x counter 0 else sliceLoop lst x y 0;; 
(* Question 5-- uses a helper function to help put together a equals and 
   not equals list, with the latter then being used in the recursive function 
   equiv x lst *) 
let rec equivs fn lst = let rec equivHelper tlist flist f = function
    |[]-> [tlist]@(equivs fn flist)
    |[x] -> if f (List.hd tlist) x = true then equivHelper (tlist@[x]) flist f []
        else equivHelper tlist (flist@[x]) f []
    |h::t-> if f (List.hd tlist) h = true then equivHelper (tlist@[h]) flist f t
        else equivHelper tlist (flist@[h]) f t in match lst with
  |[]-> []
  |[x] -> [[x]]
  |h::t -> equivHelper [h] [] fn t;; 
(* Question 6-- Can use a helper method that checks if a number is prime + 
   recusion to find a pair of prime numbers that add up to the inputted even
   number *)
let isPrimeCheck n = if n=1 then false else if n = 2 then true 
  else let rec primeCheck d = if d = n-1 then d*d > n || (n mod d != 0) else 
           d*d > n || (n mod d != 0 && primeCheck(d+1)) in primeCheck 2;;
let goldbachpair n = let rec goldbachReturn d = if (isPrimeCheck d = true && 
                                                    isPrimeCheck (n-d) = true)
                       then if n-d > d then (d, n-d) else (n-d, d)
                       else goldbachReturn (d+1) in goldbachReturn 2;; 
(*Question 7--Uses pattern matching to check outputs of both functions, if result
is false for even one output, then code should print false *) 
let rec equiv_on f g lst = 
  match lst with
  | [] -> false
  | [x] -> if f x != g x then false else true
  | h::t -> if f h != g h then false  else equiv_on f g t;; 
(* Question 8-- pairwiseFilter compares two elements at a time using 
   first::second::rest in pattern matching with the inputted function *)
let rec pairwiseFilter cmp lst = 
  match lst with
  | [] -> []
  | [first] -> [first]
  | first::second::rest -> (cmp first second)::pairwiseFilter cmp rest;;
(* Question 9-- uses a pattern matching of first::second::rest to create the in-
   putted polynomial function*)
let rec make_polynomial one two x = 
  one * (pow x two);;
let rec polynomial lst = match lst with
  | [] -> fun zeroFN -> 0 
  | (first, second)::rest -> fun x -> (make_polynomial first second 
                                         x) + ((polynomial rest)x);; 
(* Question 10-- Can use induction plus a helper method that adds the current
element in the list to all lists in the list list*) 
let rec phelper2 nestlst el = match nestlst with
  |[]-> nestlst
  |h::t -> (let g = h@[el] in g::(phelper2 t el));;
let powerset lst = let rec 
  powersetHelper lst nestlst c = match lst with
    |[] -> nestlst
    |[x] -> if c=0 then nestlst@[[x]] else nestlst@(phelper2 nestlst x)
    |h::t -> if c=0 then (powersetHelper t (nestlst@[[h]]) (c+1))
        else powersetHelper t (nestlst@(phelper2 nestlst h)) (c+1) in 
  powersetHelper lst [[]] 0;; 
