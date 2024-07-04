import {ChangeEvent, useEffect, useState} from 'react';

const useGenericSearch = (items: any[], searchKey: string) => {
    const [searchQuery, setSearchQuery] = useState<string>("");
    const [filteredItems, setFilteredItems] = useState<any[]>(items);

    useEffect(() => {
        if (searchQuery) {
            setFilteredItems(
                items.filter(item =>
                    item[searchKey].toLowerCase().includes(searchQuery.toLowerCase())
                )
            );
        } else {
            setFilteredItems(items);
        }
    }, [searchQuery, items, searchKey]);

    const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
        setSearchQuery(event.target.value);
    };

    return {
        searchQuery,
        filteredItems,
        handleSearchChange,
    };
};

export default useGenericSearch;
