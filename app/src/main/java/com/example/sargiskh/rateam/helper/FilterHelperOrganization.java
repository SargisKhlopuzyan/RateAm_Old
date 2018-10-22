package com.example.sargiskh.rateam.helper;

import com.example.sargiskh.rateam.enums.CurrencyTypeEnum;
import com.example.sargiskh.rateam.enums.ExchangeTypeEnum;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.CurrencyList;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    //TODO -Need to be implemented for all cases

    public static Comparator<Organization> OrganizationComparatorForSaleByAscendingOrderFor = new Comparator<Organization>() {

        public int compare(Organization organization1, Organization organization2) {

            Float buy1 = organization1.currencyList.USD.cash.buy;
            Float buy2 = organization2.currencyList.USD.cash.buy;

            //ascending order
//            return buy1.compareTo(buy2);

            //descending order
            return buy1.compareTo(buy2);
        }

    };

}
