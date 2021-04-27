findPalindrome = function(){
    var numbers = db.phones.find({},{_id : 1}).toArray();
    var palindrome_numbers = [];

    for(var number_idx = 0; number_idx < numbers.length; number_idx++ ){

        var number = numbers[number_idx];
        var id = number._id.toString().slice(3);

        var isPalindrome = true;

        while(id.length > 1){
            if(id[0] != id[id.length-1]){
                isPalindrome = false;
            }  
            
            id = id.slice(1,-1);
        }

        if(isPalindrome){
            palindrome_numbers.push(number);
        }
    }

    return palindrome_numbers;
}