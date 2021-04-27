phone_by_country_counter = function(){
    return db.phones.aggregate([{$group : {_id : "$components.prefix", no_phones : {$sum : 1}}}])
}