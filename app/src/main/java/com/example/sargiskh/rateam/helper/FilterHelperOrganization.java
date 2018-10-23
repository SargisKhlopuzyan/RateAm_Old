package com.example.sargiskh.rateam.helper;

import com.example.sargiskh.rateam.enums.CurrencyTypeEnum;
import com.example.sargiskh.rateam.enums.ExchangeTypeEnum;
import com.example.sargiskh.rateam.enums.SortOrderEnum;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.CurrencyList;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class FilterHelperOrganization {

    private FilterHelperOrganization() {
    }

    public static Map<String, Organization> getOrganizationFilteredListByCurrencyTypeAndExchangeType(Map<String, Organization> organizationMap, CurrencyTypeEnum currencyType, ExchangeTypeEnum exchangeType) {

        Map<String, Organization> filteredOrganizationMap = new HashMap<>();

        for (Map.Entry<String, Organization> organizationEntry: organizationMap.entrySet()) {
            if (checkIfCurrencyListContainsExchangeTypeAndCurrencyType(organizationEntry.getValue().currencyList, exchangeType, currencyType)) {
                filteredOrganizationMap.put(organizationEntry.getKey(), organizationEntry.getValue());
            }
        }


        return filteredOrganizationMap;
    }

    private static boolean checkIfCurrencyListContainsExchangeTypeAndCurrencyType(CurrencyList currencyList, ExchangeTypeEnum exchangeType, CurrencyTypeEnum currencyType) {

        if (currencyList == null) {
            return false;
        }
        switch (exchangeType) {
            case Cash:
                switch (currencyType) {
                    case AUD:
                        if (currencyList.AUD != null && currencyList.AUD.cash != null) {
                            return true;
                        }
                        break;
                    case CAD:
                        if (currencyList.CAD != null && currencyList.CAD.cash != null) {
                            return true;
                        }
                        break;
                    case XAU:
                        if (currencyList.XAU != null && currencyList.XAU.cash != null) {
                            return true;
                        }
                        break;
                    case USD:
                        if (currencyList.USD != null && currencyList.USD.cash != null) {
                            return true;
                        }
                        break;
                    case RUR:
                        if (currencyList.RUR != null && currencyList.RUR.cash != null) {
                            return true;
                        }
                        break;
                    case JPY:
                        if (currencyList.JPY != null && currencyList.JPY.cash != null) {
                            return true;
                        }
                        break;
                    case GEL:
                        if (currencyList.GEL != null && currencyList.GEL.cash != null) {
                            return true;
                        }
                        break;
                    case GBP:
                        if (currencyList.GBP != null && currencyList.GBP.cash != null) {
                            return true;
                        }
                        break;
                    case EUR:
                        if (currencyList.EUR != null && currencyList.EUR.cash != null) {
                            return true;
                        }
                        break;
                    case CHF:
                        if (currencyList.CHF != null && currencyList.CHF.cash != null) {
                            return true;
                        }
                        break;
                }
                break;
            case NonCash:
                switch (currencyType) {
                    case AUD:
                        if (currencyList.AUD != null && currencyList.AUD.nonCash != null) {
                            return true;
                        }
                        break;
                    case CAD:
                        if (currencyList.CAD != null && currencyList.CAD.nonCash != null) {
                            return true;
                        }
                        break;
                    case XAU:
                        if (currencyList.XAU != null && currencyList.XAU.nonCash != null) {
                            return true;
                        }
                        break;
                    case USD:
                        if (currencyList.USD != null && currencyList.USD.nonCash != null) {
                            return true;
                        }
                        break;
                    case RUR:
                        if (currencyList.RUR != null && currencyList.RUR.nonCash != null) {
                            return true;
                        }
                        break;
                    case JPY:
                        if (currencyList.JPY != null && currencyList.JPY.nonCash != null) {
                            return true;
                        }
                        break;
                    case GEL:
                        if (currencyList.GEL != null && currencyList.GEL.nonCash != null) {
                            return true;
                        }
                        break;
                    case GBP:
                        if (currencyList.GBP != null && currencyList.GBP.nonCash != null) {
                            return true;
                        }
                        break;
                    case EUR:
                        if (currencyList.EUR != null && currencyList.EUR.nonCash != null) {
                            return true;
                        }
                        break;
                    case CHF:
                        if (currencyList.CHF != null && currencyList.CHF.nonCash != null) {
                            return true;
                        }
                        break;
                }
                break;
        }

        return false;
    }

    ///////////////////

    public static List<CurrencyTypeEnum> getCurrencyListContainedInOrganizationForExchangeType(Organization organization, ExchangeTypeEnum exchangeType) {

        List<CurrencyTypeEnum> currencyTypeList = new ArrayList<>();

        for (CurrencyTypeEnum currencyType: CurrencyTypeEnum.values()) {
            if (checkIfCurrencyListContainsExchangeTypeAndCurrencyType(organization.currencyList, exchangeType, currencyType)) {
                currencyTypeList.add(currencyType);
            }
        }

        return currencyTypeList;
    }

    public static Map<CurrencyTypeEnum, Transaction> getOrganizationTransactionListByExchangeType(Organization organization, ExchangeTypeEnum exchangeType) {

        Map<CurrencyTypeEnum, Transaction> filteredTransactionMap = new HashMap<>();

        CurrencyList currencyList = organization.currencyList;
        if (currencyList == null) {
            return filteredTransactionMap;
        }

        for (CurrencyTypeEnum currencyType: CurrencyTypeEnum.values()) {

            switch (exchangeType) {
                case Cash:
                    switch (currencyType) {
                        case AUD:
                            if (currencyList.AUD != null && currencyList.AUD.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.AUD, currencyList.AUD.cash);
                            }
                            break;
                        case CAD:
                            if (currencyList.CAD != null && currencyList.CAD.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.CAD, currencyList.CAD.cash);
                            }
                            break;
                        case XAU:
                            if (currencyList.XAU != null && currencyList.XAU.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.XAU, currencyList.XAU.cash);
                            }
                            break;
                        case USD:
                            if (currencyList.USD != null && currencyList.USD.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.USD, currencyList.USD.cash);
                            }
                            break;
                        case RUR:
                            if (currencyList.RUR != null && currencyList.RUR.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.RUR, currencyList.RUR.cash);
                            }
                            break;
                        case JPY:
                            if (currencyList.JPY != null && currencyList.JPY.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.JPY, currencyList.JPY.cash);
                            }
                            break;
                        case GEL:
                            if (currencyList.GEL != null && currencyList.GEL.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.GEL, currencyList.GEL.cash);
                            }
                            break;
                        case GBP:
                            if (currencyList.GBP != null && currencyList.GBP.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.GBP, currencyList.GBP.cash);
                            }
                            break;
                        case EUR:
                            if (currencyList.EUR != null && currencyList.EUR.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.EUR, currencyList.EUR.cash);
                            }
                            break;
                        case CHF:
                            if (currencyList.CHF != null && currencyList.CHF.cash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.CHF, currencyList.CHF.cash);
                            }
                            break;
                    }
                    break;
                case NonCash:
                    switch (currencyType) {
                        case AUD:
                            if (currencyList.AUD != null && currencyList.AUD.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.AUD, currencyList.AUD.nonCash);
                            }
                            break;
                        case CAD:
                            if (currencyList.CAD != null && currencyList.CAD.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.CAD, currencyList.CAD.nonCash);
                            }
                            break;
                        case XAU:
                            if (currencyList.XAU != null && currencyList.XAU.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.XAU, currencyList.XAU.nonCash);
                            }
                            break;
                        case USD:
                            if (currencyList.USD != null && currencyList.USD.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.USD, currencyList.USD.nonCash);
                            }
                            break;
                        case RUR:
                            if (currencyList.RUR != null && currencyList.RUR.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.RUR, currencyList.RUR.nonCash);
                            }
                            break;
                        case JPY:
                            if (currencyList.JPY != null && currencyList.JPY.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.JPY, currencyList.JPY.nonCash);
                            }
                            break;
                        case GEL:
                            if (currencyList.GEL != null && currencyList.GEL.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.GEL, currencyList.GEL.nonCash);
                            }
                            break;
                        case GBP:
                            if (currencyList.GBP != null && currencyList.GBP.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.GBP, currencyList.GBP.nonCash);
                            }
                            break;
                        case EUR:
                            if (currencyList.EUR != null && currencyList.EUR.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.EUR, currencyList.EUR.nonCash);
                            }
                            break;
                        case CHF:
                            if (currencyList.CHF != null && currencyList.CHF.nonCash != null) {
                                filteredTransactionMap.put(CurrencyTypeEnum.CHF, currencyList.CHF.nonCash);
                            }
                            break;
                    }
                    break;
            }
        }

        return filteredTransactionMap;
    }


    public static TreeMap<String, Organization> sortMapForPurchase(Map<String, Organization> map, ExchangeTypeEnum exchangeType, CurrencyTypeEnum currencyType, SortOrderEnum sortOrder){
        Comparator<String> comparator = new ComparatorForPurchase(map, exchangeType, currencyType, sortOrder);
        //TreeMap is a map sorted by its keys.
        //The comparator is used to sort the TreeMap by keys.
        TreeMap<String, Organization> result = new TreeMap<String, Organization>(comparator);
        result.putAll(map);
        return result;
    }

    public static TreeMap<String, Organization> sortMapForSale(Map<String, Organization> map, ExchangeTypeEnum exchangeType, CurrencyTypeEnum currencyType, SortOrderEnum sortOrder){
        Comparator<String> comparator = new ComparatorForSale(map, exchangeType, currencyType, sortOrder);
        //TreeMap is a map sorted by its keys.
        //The comparator is used to sort the TreeMap by keys.
        TreeMap<String, Organization> result = new TreeMap<String, Organization>(comparator);
        result.putAll(map);
        return result;
    }

    static class ComparatorForPurchase implements Comparator<String>{

        Map<String, Organization> map = new HashMap<String, Organization>();
        ExchangeTypeEnum exchangeType;
        CurrencyTypeEnum currencyType;
        SortOrderEnum sortOrder;

        public ComparatorForPurchase(Map<String, Organization> map, ExchangeTypeEnum exchangeType, CurrencyTypeEnum currencyType, SortOrderEnum sortOrder){
            this.map.putAll(map);
            this.exchangeType = exchangeType;
            this.currencyType = currencyType;
            this.sortOrder = sortOrder;
        }

        @Override
        public int compare(String s1, String s2) {

            switch (exchangeType) {
                case Cash:
                    switch (currencyType) {
                        case AUD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.AUD.cash.buy >= map.get(s2).currencyList.AUD.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.AUD.cash.buy <= map.get(s2).currencyList.AUD.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case CAD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.CAD.cash.buy >= map.get(s2).currencyList.CAD.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.CAD.cash.buy <= map.get(s2).currencyList.CAD.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case XAU:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.XAU.cash.buy >= map.get(s2).currencyList.XAU.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.XAU.cash.buy <= map.get(s2).currencyList.XAU.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case USD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.USD.cash.buy >= map.get(s2).currencyList.USD.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.USD.cash.buy <= map.get(s2).currencyList.USD.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case RUR:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.RUR.cash.buy >= map.get(s2).currencyList.RUR.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.RUR.cash.buy <= map.get(s2).currencyList.RUR.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case JPY:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.JPY.cash.buy >= map.get(s2).currencyList.JPY.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.JPY.cash.buy <= map.get(s2).currencyList.JPY.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case GEL:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.GEL.cash.buy >= map.get(s2).currencyList.GEL.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.GEL.cash.buy <= map.get(s2).currencyList.GEL.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case GBP:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.GBP.cash.buy >= map.get(s2).currencyList.GBP.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.GBP.cash.buy <= map.get(s2).currencyList.GBP.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case EUR:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.EUR.cash.buy >= map.get(s2).currencyList.EUR.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.EUR.cash.buy <= map.get(s2).currencyList.EUR.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case CHF:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.CHF.cash.buy >= map.get(s2).currencyList.CHF.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.CHF.cash.buy <= map.get(s2).currencyList.CHF.cash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                    }
                    break;
                case NonCash:
                    switch (currencyType) {
                        case AUD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.AUD.nonCash.buy >= map.get(s2).currencyList.AUD.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.AUD.nonCash.buy <= map.get(s2).currencyList.AUD.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case CAD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.CAD.nonCash.buy >= map.get(s2).currencyList.CAD.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.CAD.nonCash.buy <= map.get(s2).currencyList.CAD.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case XAU:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.XAU.nonCash.buy >= map.get(s2).currencyList.XAU.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.XAU.nonCash.buy <= map.get(s2).currencyList.XAU.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case USD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.USD.nonCash.buy >= map.get(s2).currencyList.USD.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.USD.nonCash.buy <= map.get(s2).currencyList.USD.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case RUR:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.RUR.nonCash.buy >= map.get(s2).currencyList.RUR.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.RUR.nonCash.buy <= map.get(s2).currencyList.RUR.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case JPY:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.JPY.nonCash.buy >= map.get(s2).currencyList.JPY.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.JPY.nonCash.buy <= map.get(s2).currencyList.JPY.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case GEL:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.GEL.nonCash.buy >= map.get(s2).currencyList.GEL.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.GEL.nonCash.buy <= map.get(s2).currencyList.GEL.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case GBP:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.GBP.nonCash.buy >= map.get(s2).currencyList.GBP.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.GBP.nonCash.buy <= map.get(s2).currencyList.GBP.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case EUR:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.EUR.nonCash.buy >= map.get(s2).currencyList.EUR.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.EUR.nonCash.buy <= map.get(s2).currencyList.EUR.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case CHF:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.CHF.nonCash.buy >= map.get(s2).currencyList.CHF.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.CHF.nonCash.buy <= map.get(s2).currencyList.CHF.nonCash.buy) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                    }
                    break;
            }
            return 1;
        }
    }

    static class ComparatorForSale implements Comparator<String>{

        Map<String, Organization> map = new HashMap<String, Organization>();
        ExchangeTypeEnum exchangeType;
        CurrencyTypeEnum currencyType;
        SortOrderEnum sortOrder;

        public ComparatorForSale(Map<String, Organization> map, ExchangeTypeEnum exchangeType, CurrencyTypeEnum currencyType, SortOrderEnum sortOrder){
            this.map.putAll(map);
            this.exchangeType = exchangeType;
            this.currencyType = currencyType;
            this.sortOrder = sortOrder;
        }

        @Override
        public int compare(String s1, String s2) {

            switch (exchangeType) {
                case Cash:
                    switch (currencyType) {
                        case AUD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.AUD.cash.sell >= map.get(s2).currencyList.AUD.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.AUD.cash.sell <= map.get(s2).currencyList.AUD.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case CAD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.CAD.cash.sell >= map.get(s2).currencyList.CAD.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.CAD.cash.sell <= map.get(s2).currencyList.CAD.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case XAU:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.XAU.cash.sell >= map.get(s2).currencyList.XAU.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.XAU.cash.sell <= map.get(s2).currencyList.XAU.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case USD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.USD.cash.sell >= map.get(s2).currencyList.USD.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.USD.cash.sell <= map.get(s2).currencyList.USD.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case RUR:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.RUR.cash.sell >= map.get(s2).currencyList.RUR.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.RUR.cash.sell <= map.get(s2).currencyList.RUR.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case JPY:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.JPY.cash.sell >= map.get(s2).currencyList.JPY.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.JPY.cash.sell <= map.get(s2).currencyList.JPY.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case GEL:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.GEL.cash.sell >= map.get(s2).currencyList.GEL.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.GEL.cash.sell <= map.get(s2).currencyList.GEL.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case GBP:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.GBP.cash.sell >= map.get(s2).currencyList.GBP.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.GBP.cash.sell <= map.get(s2).currencyList.GBP.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case EUR:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.EUR.cash.sell >= map.get(s2).currencyList.EUR.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.EUR.cash.sell <= map.get(s2).currencyList.EUR.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case CHF:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.CHF.cash.sell >= map.get(s2).currencyList.CHF.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.CHF.cash.sell <= map.get(s2).currencyList.CHF.cash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                    }
                    break;
                case NonCash:
                    switch (currencyType) {
                        case AUD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.AUD.nonCash.sell >= map.get(s2).currencyList.AUD.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.AUD.nonCash.sell <= map.get(s2).currencyList.AUD.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case CAD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.CAD.nonCash.sell >= map.get(s2).currencyList.CAD.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.CAD.nonCash.sell <= map.get(s2).currencyList.CAD.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case XAU:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.XAU.nonCash.sell >= map.get(s2).currencyList.XAU.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.XAU.nonCash.sell <= map.get(s2).currencyList.XAU.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case USD:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.USD.nonCash.sell >= map.get(s2).currencyList.USD.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.USD.nonCash.sell <= map.get(s2).currencyList.USD.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case RUR:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.RUR.nonCash.sell >= map.get(s2).currencyList.RUR.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.RUR.nonCash.sell <= map.get(s2).currencyList.RUR.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case JPY:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.JPY.nonCash.sell >= map.get(s2).currencyList.JPY.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.JPY.nonCash.sell <= map.get(s2).currencyList.JPY.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case GEL:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.GEL.nonCash.sell >= map.get(s2).currencyList.GEL.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.GEL.nonCash.sell <= map.get(s2).currencyList.GEL.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case GBP:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.GBP.nonCash.sell >= map.get(s2).currencyList.GBP.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            } else {
                                if (map.get(s1).currencyList.GBP.nonCash.sell <= map.get(s2).currencyList.GBP.nonCash.sell) {
                                    return 1;
                                } else {
                                    return -1;
                                }
                            }
                        case EUR:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.EUR.nonCash.sell >= map.get(s2).currencyList.EUR.nonCash.sell) {
                                    return 1;
                                }
                            } else {
                                if (map.get(s1).currencyList.EUR.nonCash.sell <= map.get(s2).currencyList.EUR.nonCash.sell) {
                                    return 1;
                                }
                            }
                        case CHF:
                            if (sortOrder == SortOrderEnum.Ascending) {
                                if (map.get(s1).currencyList.CHF.nonCash.sell >= map.get(s2).currencyList.CHF.nonCash.sell) {
                                    return 1;
                                }
                            } else {
                                if (map.get(s1).currencyList.CHF.nonCash.sell <= map.get(s2).currencyList.CHF.nonCash.sell) {
                                    return 1;
                                }
                            }
                    }
                    break;
            }
            return 1;
        }
    }
}
