
addPredicate("diff",2)
addPredicate("equals",2)
function mkEquals()
    constants = getConstants()
    for i,v in pairs(constants) do
        setInitial("equals",{v,v})
    end
end

function mkDiff()
    constants = getConstants()
    for i,v in pairs(constants) do
        for ii,vv in pairs(constants) do
            if( i~=ii) then
                setInitial("diff",{v,vv})
                setInitial("diff",{vv,v})
            end
        end
    end
end