for $x in /information/person
    return if ($x/gender="Male")
        then <male>{data($x/name)}</male>
        else <female>{data($x/name)}</female>
