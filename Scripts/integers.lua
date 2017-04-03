

addPredicate('integer',1)
addPredicate('zero',1)
initialized = false
intPrefix = ''
intFrom = 0
intTo = 0
intOldUses = uses

function uses(what)
    if( what == 'succ') then
        addPredicate('succ',2)
    elseif( what == 'less') then
        addPredicate('less',2)
    elseif( what == 'leq') then
        addPredicate('leq',2)
    elseif( what == 'sum') then
        addPredicate('sum',3)
    end
    
    intOldUses(what)

end
    
function getName(i)
    if (i < 0 ) then
        return intPrefix .. 'minus'..(0- i)
    else
        return intPrefix..i
    end
end

function mkInteger(prefix,from,to)
    if(to < from ) then
        error("to should not be less than from")
    end
    intPrefix = prefix
    intFrom = tonumber(from)
    intTo = tonumber(to)
    for i=from, to do
        name =  getName(i)
        addConstant(getName(i))
        setInitial('integer',{getName(i)})
    end
    if( from <= 0 and to >=0 ) then
        setInitial('zero', {prefix .. 0})
    end
    initialized = true
    if(isNeeded('less')) then
        mkLess()
    end
    if(isNeeded('leq')) then
        mkLeq()
    end
    if(isNeeded('succ')) then
        mkSucc()
    end
    if(isNeeded('sum')) then
        mkSum()
    end
    
end

function mkLess()
    if(not initialized) then
        error("you should run mkInteger first")
    end
    for i=intFrom, intTo  do
        for j=0, i-1 do
            setInitial('less',{getName(j),getName(i)})
        end
    end
end

function mkLeq()
    if(not initialized) then
        error("you should run mkInteger first")
    end
    for i=intFrom, intTo  do
        for j=0, i do
            setInitial('leq',{getName(j),getName(i)})
        end
    end
end
function mkSum()
    if(not initialized) then
        error("you should run mkInteger first")
    end
    for i = intTo, intFrom ,-1 do
        for j = i, intFrom , -1 do
            setInitial('sum',{getName(j),getName(i-j),getName(i)})
            setInitial('sum',{getName(i-j),getName(j),getName(i)})
        end
    end
end

function mkSucc()
    if(not initialized) then
        error("you should run mkInteger first")
    end
    for i = intFrom, (intTo - 1) do
        setInitial('succ',{getName(i),getName(i+1)})
    end
end