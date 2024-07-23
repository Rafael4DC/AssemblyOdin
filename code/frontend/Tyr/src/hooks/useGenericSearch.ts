import {ChangeEvent, useEffect, useState} from 'react';

/**
 * Hook to manage the search of a list of items
 *
 * @param items the list of items to search
 * @param searchKey the key to search in the items
 * @returns the search query, the filtered items and the function to handle the search change
 */
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
