

addPredicate('integer',1)
addPredicate('zero',1)
addPredicate('succ',2)


function mkInteger(prefix, upto)
    if(upto < 0 ) then
        error("upto should not be negative")
    end
    for i=0, upto do
        name =  prefix .. i
        addConstant(name)
        setInitial('integer',{name})
    end
    setInitial('zero', {prefix .. 0})
end

function lessUpTo(prefix, upto)
    if(upto < 0 ) then
        error("upto should not be negative")
    end
    addPredicate('less',2)
    for i=0, upto  do
        for j=0, i-1 do
            setInitial('less',{prefix..j,prefix..i})
        end
    end
end

function mkSucc(prefix,upto)
    if(upto < 0 ) then
        error("upto should not be negative")
    end
    addPredicate('succ',2)
    for i = 0, upto -1 do
        setInitial('succ',{prefix..i,prefix..(i+1)})
    end
end